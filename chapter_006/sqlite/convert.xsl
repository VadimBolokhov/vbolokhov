<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
	<xsl:text>&#xA;</xsl:text>
	    <entries>
		<xsl:text>&#xA;</xsl:text>
	        <xsl:for-each select="entries/entry">
			    <entry>
				    <xsl:attribute name="field">
					    <xsl:value-of select="field"/>
					</xsl:attribute>
				</entry>
				<xsl:text>&#xA;</xsl:text>
			</xsl:for-each>
	    </entries>
    </xsl:template>

</xsl:stylesheet>