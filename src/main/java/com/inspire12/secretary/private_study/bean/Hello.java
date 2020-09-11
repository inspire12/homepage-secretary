package com.inspire12.secretary.private_study.bean;


public class Hello {
    String name;
    Printer printer;

    public void print() {
        this.printer.print("Hello "+ name);
    }

    public void setPrinter(Printer printer) {
        this.printer = printer;
    }
}
