
package ru.javaops.masterjava.xml.schema2;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for periodType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="periodType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="FINISHED"/>
 *     &lt;enumeration value="CURRENT"/>
 *     &lt;enumeration value="REGISTERING"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "periodType", namespace = "http://javaops.ru")
@XmlEnum
public enum PeriodType {

    FINISHED,
    CURRENT,
    REGISTERING;

    public String value() {
        return name();
    }

    public static PeriodType fromValue(String v) {
        return valueOf(v);
    }

}
