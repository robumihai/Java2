package org.example.command;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.example.exception.CatalogException;
import org.example.repository.Catalog;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class ReportCommand implements Command {
    private Catalog catalog;
    private String outputPath;

    public ReportCommand(Catalog catalog, String outputPath) {
        this.catalog = catalog;
        this.outputPath = outputPath;
    }

    @Override
    public void execute() throws CatalogException {
        try {
            // Generam un sablon (template) simplu direct din cod, ca sa nu mai creezi tu fisiere extra
            String templateContent = "<html><head><title>Catalog Report</title></head><body>" +
                    "<h1>Raport Catalog: ${catalog.name}</h1><ul>" +
                    "<#list catalog.resources as res>" +
                    "<li><b>${res.title}</b> (${res.location})</li>" +
                    "</#list></ul></body></html>";

            File templateFile = new File("report_template.ftl");
            try (FileWriter fw = new FileWriter(templateFile)) {
                fw.write(templateContent);
            }

            // Configuram FreeMarker
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
            cfg.setDirectoryForTemplateLoading(new File("."));
            Template template = cfg.getTemplate("report_template.ftl");

            // Trimitem datele catre template
            Map<String, Object> data = new HashMap<>();
            data.put("catalog", catalog);

            // Generam fisierul HTML
            File reportFile = new File(outputPath);
            try (Writer fileWriter = new FileWriter(reportFile)) {
                template.process(data, fileWriter);
            }

            System.out.println("Raport generat la: " + reportFile.getAbsolutePath());

            // Deschidem raportul generat in browser
            Desktop.getDesktop().browse(reportFile.toURI());

        } catch (Exception e) {
            throw new CatalogException("Eroare la generarea raportului HTML.", e);
        }
    }
}