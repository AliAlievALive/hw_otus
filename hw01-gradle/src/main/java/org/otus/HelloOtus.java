package org.otus;

import com.google.common.collect.Lists;
import java.util.List;

@SuppressWarnings("java:S106")
public class HelloOtus {
    public static void main(String[] args) {
        List<String> names = Lists.newArrayList("Serj", "Petr", "Ali");

        System.out.println(Lists.reverse(names));
    }
}
