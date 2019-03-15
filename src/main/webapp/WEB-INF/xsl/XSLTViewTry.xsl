<?xml version='1.0' encoding='UTF-8' ?> 
<!-- was: <?xml version="1.0"?> -->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
		version="1.0">
	<xsl:output method="html" indent="yes" />
	<xsl:template match="/">
		<html> 
			<body>
				<h2>My CD Collection</h2>
				<apply-templates/>
				<table border="5">
					<tr bgcolor="#9acd32">
						<th style="text-align:center">title</th>
						<th style="text-align:center">artist</th>
						<th style="text-align:center">country</th>
						<xsl:for-each select="catalog/cd/title2">
							<tr>
								<xsl:for-each select="title3">
									<td>
										<xsl:value-of select="title"/>
									</td>
									<td>
										<xsl:value-of select="artist"/>
									</td>
									<td>
										<xsl:value-of select="country"/>
									</td>
								</xsl:for-each>
							</tr>
						</xsl:for-each>
					</tr>
				</table>
			</body>
				
		</html>
	</xsl:template>			
</xsl:stylesheet>

