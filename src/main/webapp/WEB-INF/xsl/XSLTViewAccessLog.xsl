<?xml version='1.0' encoding='UTF-8' ?> 
<!-- was: <?xml version="1.0"?> -->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
		version="1.0">
	<xsl:output method="html" indent="yes" />
	<xsl:template match="/">
		<html> 
			<head>
				<title>AccessLogSearch</title>
			</head>
			<body>	
				<nav>
					<div>
						<form>
							<div>
								<th>
									<label>網頁狀態</label>
									<input name="httpstatus" type="text" placeholder="例如:500"/>
									<label>造訪日期</label>
									<input name="unixtimestamp" type="date"/>
									<label>請求方法</label>
									<select name="requestmethod">
										<option value="GET">GET</option>
										<option value="POST">POST</option>
										<option value="DELETE">DELETE</option>
										<option value="PUT">PUT</option>
									</select>
								</th>
							</div>
							<button name="search" type="submit" value="搜尋">搜尋</button>
							<b>
								<a href="accessLogSearch">搜尋</a>
							</b>
						</form>
					</div>
				</nav>
				<tbody>
					<caption>
						<form>
							<table border="5">
								<thead></thead>
								<tfoot></tfoot>
								<tbody>
									<tr>
										<th style="text-align:center">id</th>
										<th style="text-align:center">remotehost</th>
										<th style="text-align:center">username</th>
										<th style="text-align:center">unixtimestamp</th>
										<th style="text-align:center">virtualhost</th>
										<th style="text-align:center">httpmethod</th>
										<th style="text-align:center">querystring</th>
										<th style="text-align:center">httpstatus</th>
										<th style="text-align:center">bytes</th>
										<th style="text-align:center">referer</th>
										<th style="text-align:center">useragent</th>
										<th style="text-align:center">pageNumber</th>
										<th style="text-align:center">recordsPerPage</th>
										<th style="text-align:center">numberOfRecords</th>
									</tr>
									<xsl:for-each select="accesslog/log">
										<tr>
											<td>
												<xsl:value-of select="id"/>
											</td>
											<td>
												<xsl:value-of select="remotehost"/>
											</td>
											<td>
												<xsl:value-of select="username"/>
											</td>
											<td>
												<xsl:value-of select="unixtimestamp"/>
											</td>
											<td>
												<xsl:value-of select="virtualhost"/>
											</td>
											<td>
												<xsl:value-of select="httpmethod"/>
											</td>
											<td>
												<xsl:value-of select="querystring"/>
											</td>
											<td>
												<xsl:value-of select="httpstatus"/>
											</td>
											<td>
												<xsl:value-of select="bytes"/>
											</td>
											<td>
												<xsl:value-of select="referer"/>
											</td>
											<td>
												<xsl:value-of select="useragent"/>
											</td>
											<td>
												<xsl:value-of select="pagenumber"/>
											</td>
											<td>
												<xsl:value-of select="recordsperpage"/>
											</td>
											<td>
												<xsl:value-of select="numberofrecords"/>
											</td>
										</tr>
									</xsl:for-each>
								</tbody>
							</table>
						</form>
					</caption>
				</tbody>
			</body>	
		</html>
	</xsl:template>	
</xsl:stylesheet>

