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
				<xsl:apply-templates select="/*/biosim:SimBioModules/biosim:environment" />
				<xsl:apply-templates select="/*/biosim:SimBioModules/biosim:air" />
			</body>
		</html>
	</xsl:template>
	
	<!-- environments -->
	<xsl:template match="biosim:SimBioModules/biosim:environment">
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
	
	<!-- air -->
	<xsl:template match="biosim:SimBioModules/biosim:air">
		<h2>Buffers</h2>
		<table border="1">
			<tr bgcolor="cyan">
				<th align="left">Name</th>
				<th align="left">Capacity</th>
				<th align="left">Level</th>
			</tr>
			<xsl:for-each select="biosim:NitrogenStore">
				<tr>
					<td>
						<xsl:value-of select="@moduleName" />
					</td>
					<td>
						<xsl:value-of select="@capacity" />
					</td>
					<td>
						<xsl:value-of select="@level" />
					</td>
				</tr>
			</xsl:for-each>
		</table>
	</xsl:template>
</xsl:transform>
