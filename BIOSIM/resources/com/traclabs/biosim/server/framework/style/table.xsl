<?xml version="1.0"?>

<xsl:transform version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:biosim="http://www.traclabs.com/biosim">
	<xsl:template match="/">
		<html>
			<head>
				<title>BioSim Configuration File</title>
				<meta http-equiv="content-type"
					content="text/html; charset=ISO-8859-1" />
				<meta name="author" content="Scott Bell" />
				<meta name="description"
					content="A tabled display of a life support configuration" />
			</head>
			<body>
				<xsl:apply-templates select="/biosim:biosim/biosim:SimBioModules/biosim:crew" />
				<xsl:apply-templates select="/biosim:biosim/biosim:SimBioModules/biosim:environment" />
				<xsl:apply-templates select="/biosim:biosim/biosim:Sensors" />
				<xsl:apply-templates select="/biosim:biosim/biosim:SimBioModules" />
			</body>
		</html>
	</xsl:template>
	
	<!-- environments -->
	<xsl:template match="/biosim:biosim/biosim:SimBioModules/biosim:environment">
		<h2>Environments</h2>
		<table border="1">
			<tr bgcolor="#9acd32">
				<th align="left">Name</th>
				<th align="left">Volume</th>
			</tr>
			<xsl:for-each select="biosim:SimEnvironment">
				<tr>
					<td>
						<xsl:value-of select="@moduleName" />
					</td>
					<td>
						<xsl:value-of select="@initialVolume" />
					</td>
				</tr>
			</xsl:for-each>
		</table>
	</xsl:template>
	
	<!-- crew -->
	<xsl:template match="/biosim:biosim/biosim:SimBioModules/biosim:crew" >
		<h2>Crew</h2>
		<table border="1">
			<tr bgcolor="orange">
				<th align="left">Name</th>
				<th align="left">Age</th>
				<th align="left">Sex</th>
				<th align="left">Weight</th>
			</tr>
			<xsl:for-each select="*/biosim:crewPerson">
				<tr>
					<td><xsl:value-of select="@name" /></td>
					<td><xsl:value-of select="@age" /></td>
					<td><xsl:value-of select="@sex" /></td>
					<td><xsl:value-of select="@weight" /></td>
				</tr>
			</xsl:for-each>
		</table>
	</xsl:template>
	
	<!-- SimBioModules -->
	<xsl:template match="/biosim:biosim/biosim:SimBioModules" >
		<h2>Stores</h2>
		<table border="1">
			<tr bgcolor="cyan">
				<th align="left">Name</th>
				<th align="left">Level</th>
				<th align="left">Capacity</th>
			</tr>
			<xsl:for-each select="*/*[contains(name(),'Store')]">
				<tr>
					<td><xsl:value-of select="@moduleName" /></td>
					<td><xsl:value-of select="@level" /></td>
					<td><xsl:value-of select="@capacity" /></td>
				</tr>
			</xsl:for-each>
		</table>
		<h2>Systems</h2>
		<table border="1">
			<tr bgcolor="red">
				<th align="left">Name</th>
				<th align="left">Inputs</th>
				<th align="left">Outputs</th>
			</tr>
			<xsl:for-each select="*/*[not(contains(name(),'Store') or contains(name(),'CrewGroup') or contains(name(),'SimEnvironment'))]">
				<tr>
					<td><xsl:value-of select="@moduleName" /></td>
					<!-- inputs -->
					<td>
						<xsl:for-each select="*[contains(name(),'Consumer')]">
							<xsl:value-of select="@inputs" /> 
								<xsl:if test="position()=last()-1">
									<xsl:text>, </xsl:text>
								</xsl:if>
						</xsl:for-each>
					</td>
					<!-- outputs -->
					<td>
						<xsl:for-each select="*[contains(name(),'Producer')]">
							<xsl:value-of select="@outputs" /> 
								<xsl:if test="position()=last()-1">
									<xsl:text>, </xsl:text>
								</xsl:if>
						</xsl:for-each>
					</td>
				</tr>
			</xsl:for-each>
		</table>
	</xsl:template>
	
	<!-- sensors -->
	<xsl:template match="/biosim:biosim/biosim:Sensors" >
		<h2>Sensors</h2>
		<table border="1">
			<tr bgcolor="yellow">
				<th align="left">Name</th>
			</tr>
			<xsl:for-each select="*/*[contains(name(),'Sensor')]">
				<tr>
					<td><xsl:value-of select="@moduleName" /></td>
				</tr>
			</xsl:for-each>
		</table>
	</xsl:template>
	
</xsl:transform>
