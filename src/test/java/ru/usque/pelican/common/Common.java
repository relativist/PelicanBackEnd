package ru.usque.pelican.common;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
class Common {
  @Test
  void test() {
//    19.01.2019
    SimpleDateFormat format = new SimpleDateFormat("dd.MM.YYYY");
    String line = format.format(new Date());
    log.info("line: {}", line);
  }
}
