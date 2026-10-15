"""One JSON line per tick. The dashboard and evaluation read this file, not BioSim."""
from __future__ import annotations

import json
from pathlib import Path
from typing import Any, Dict, List, Optional


class TickLogger:
    def __init__(self, path: Optional[Path] = None, keep_in_memory: bool = False):
        self.path = Path(path) if path else None
        self.records: List[Dict[str, Any]] = []
        self.keep_in_memory = keep_in_memory or path is None
        self._fh = None
        if self.path:
            self.path.parent.mkdir(parents=True, exist_ok=True)
            self._fh = self.path.open("w")

    def write(self, record: Dict[str, Any]) -> None:
        if self.keep_in_memory:
            self.records.append(record)
        if self._fh:
            self._fh.write(json.dumps(record) + "\n")
            self._fh.flush()  # so a live dashboard can tail the file

    def close(self) -> None:
        if self._fh:
            self._fh.close()
            self._fh = None

    def __enter__(self) -> "TickLogger":
        return self

    def __exit__(self, *exc) -> None:
        self.close()


def read_log(path: Path) -> List[Dict[str, Any]]:
    with Path(path).open() as fh:
        return [json.loads(line) for line in fh if line.strip()]
