package org.otus;

import org.otus.dto.Ioc;
import org.otus.dto.TestLoggingInterface;

public class ProxyLogging {

    public static void main(String[] args) {
        TestLoggingInterface myClass = Ioc.createMyClass();
        myClass.calculation(6);
    }
}
