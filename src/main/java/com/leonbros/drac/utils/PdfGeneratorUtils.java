package com.leonbros.drac.utils;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.Margin;
import com.microsoft.playwright.options.WaitUntilState;

import java.io.IOException;
import java.nio.file.Path;

public class PdfGeneratorUtils {

  public static void renderHtmlToPdf(String html, Path outputPdf) throws IOException {
    try (Playwright pw = Playwright.create()) {
      final Browser browser =
          pw.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
      final BrowserContext ctx = browser.newContext();
      final Page page = ctx.newPage();

      page.setContent(html, new Page.SetContentOptions().setWaitUntil(WaitUntilState.NETWORKIDLE));

      page.pdf(new Page.PdfOptions().setPath(outputPdf).setFormat("A4").setPrintBackground(true)
          //              .setMargin(new Margin().setTop("16mm").setRight("16mm").setBottom("16mm").setLeft("16mm"))
      );
      browser.close();
    }

    //    try (OutputStream os = new FileOutputStream(outputPdf.toFile())) {
    //      PdfRendererBuilder builder = new PdfRendererBuilder();
    //      builder.useFastMode();
    //      builder.withHtmlContent(html, null); // Null base uri, HTML is self-contained.
    //      builder.toStream(os);
    //      builder.run();
    //    }
  }
}
