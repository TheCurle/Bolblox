package uk.gemwire.bolblox.format.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class Property {
    @XmlAttribute(name = "name")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected java.lang.String name;

    /**
     * Gets the value of the name property.
     *
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *
     */
    public java.lang.String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *
     */
    public void setName(java.lang.String value) {
        this.name = value;
    }
}
