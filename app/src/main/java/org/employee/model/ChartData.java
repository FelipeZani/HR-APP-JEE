package org.employee.model;

public class ChartData {
    private String label;
    private Number value; // Peut être un entier (nombre d'employés) ou un double (salaire)

    public ChartData(String label, Number value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public Number getValue() { return value; }
    public void setValue(Number value) { this.value = value; }
}