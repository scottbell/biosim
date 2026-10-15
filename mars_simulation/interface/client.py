"""Thin 1:1 wrapper over the BioSim REST API. No control logic lives here.

Endpoint source: src/com/traclabs/biosim/server/framework/SimulationController.java:68-83
"""
from __future__ import annotations

from enum import Enum
from typing import Any, Dict, List, Optional

import requests


class Intensity(str, Enum):
    LOW = "LOW_MALF"
    MEDIUM = "MEDIUM_MALF"
    SEVERE = "SEVERE_MALF"


class Length(str, Enum):
    TEMPORARY = "TEMPORARY_MALF"
    PERMANENT = "PERMANENT_MALF"


class BioSimError(RuntimeError):
    def __init__(self, status: int, message: str, url: str):
        super().__init__(f"BioSim {status} at {url}: {message}")
        self.status = status
        self.message = message
        self.url = url


class BioSimClient:
    """One instance talks to one simulation once start() has been called.

    BioSim has no delete/reset endpoint, so every start() creates a new sim that
    stays in server memory until the container restarts.
    """

    def __init__(self, base_url: str = "http://localhost:8009", timeout: float = 10.0):
        self.base_url = base_url.rstrip("/")
        self.timeout = timeout
        self.sim_id: Optional[int] = None
        # keep-alive: a lettuce cycle is 672 ticks, each needing several requests
        self._session = requests.Session()

    def _url(self, path: str) -> str:
        return f"{self.base_url}{path}"

    def _sim(self, path: str = "") -> str:
        if self.sim_id is None:
            raise RuntimeError("No simulation started. Call start() first.")
        return f"/api/simulation/{self.sim_id}{path}"

    def _request(self, method: str, path: str, **kwargs) -> Any:
        url = self._url(path)
        resp = self._session.request(method, url, timeout=self.timeout, **kwargs)
        if not resp.ok:
            try:
                message = resp.json().get("error", resp.text)
            except ValueError:
                message = resp.text
            raise BioSimError(resp.status_code, message, url)
        return resp.json() if resp.content else None

    # simulation lifecycle

    def list_sims(self) -> List[int]:
        return self._request("GET", "/api/simulation")["simulations"]

    def start(self, config_xml: str) -> int:
        body = self._request(
            "POST", "/api/simulation/start",
            data=config_xml.encode("utf-8"),
            headers={"Content-Type": "application/xml"},
        )
        self.sim_id = int(body["simId"])
        return self.sim_id

    def attach(self, sim_id: int) -> None:
        self.sim_id = sim_id

    def tick(self) -> int:
        return int(self._request("POST", self._sim("/tick"))["ticks"])

    # reading

    def state(self) -> Dict[str, Any]:
        """Full state: {"globals": {...}, "modules": {name: module, ...}}."""
        return self._request("GET", self._sim())

    def globals(self) -> Dict[str, Any]:
        return self._request("GET", self._sim("/globals"))

    def module(self, name: str) -> Dict[str, Any]:
        return self._request("GET", self._sim(f"/modules/{name}"))

    # actuation
    # flow_type must be spelled as the state JSON spells it ("GreyWater", "CO2", "Power").
    # The server builds a setter name from it and only fixes the casing of "potablewater",
    # so "greywater" would fail with a 500.

    def set_consumer(self, module: str, flow_type: str, desired: List[float],
                     connections: Optional[List[str]] = None) -> None:
        self._set_definition("consumers", module, flow_type, desired, connections)

    def set_producer(self, module: str, flow_type: str, desired: List[float],
                     connections: Optional[List[str]] = None) -> None:
        self._set_definition("producers", module, flow_type, desired, connections)

    def _set_definition(self, side: str, module: str, flow_type: str,
                        desired: List[float], connections: Optional[List[str]]) -> None:
        payload: Dict[str, Any] = {"desiredFlowRates": [float(v) for v in desired]}
        if connections is not None:
            payload["connections"] = list(connections)
        self._request("POST", self._sim(f"/modules/{module}/{side}/{flow_type}"), json=payload)

    # malfunctions

    def add_malfunction(self, module: str, intensity: Intensity, length: Length,
                        tick: Optional[int] = None) -> Optional[int]:
        """Returns the malfunction id if started now. Scheduled ones return None
        (the server only sends a message); read them back with malfunctions()."""
        payload: Dict[str, Any] = {"intensity": Intensity(intensity).value,
                                   "length": Length(length).value}
        if tick is not None:
            payload["tickToOccur"] = int(tick)
        body = self._request("POST", self._sim(f"/modules/{module}/malfunctions"), json=payload)
        return body.get("malfunctionID")

    def malfunctions(self, module: str) -> List[Dict[str, Any]]:
        return self._request("GET", self._sim(f"/modules/{module}/malfunctions"))

    def clear_malfunction(self, module: str, malfunction_id: Optional[int] = None) -> None:
        path = f"/modules/{module}/malfunctions"
        if malfunction_id is not None:
            path += f"/{malfunction_id}"
        self._request("DELETE", self._sim(path))

    def close(self) -> None:
        self._session.close()
