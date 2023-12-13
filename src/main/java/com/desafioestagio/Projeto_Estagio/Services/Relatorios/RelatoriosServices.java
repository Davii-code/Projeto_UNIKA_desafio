package com.desafioestagio.Projeto_Estagio.Services.Relatorios;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.repo.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class RelatoriosServices {


    @Autowired
    private DataSource dataSource;

    public Resource exportReport() {
        try{
            JasperPrint jasperPrint = JasperFillManager.fillReport("src/main/resources/Monitorador.jasper", null, dataSource.getConnection());

            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("MONITORADORES.pdf"));

            exporter.exportReport();

            //returning the file
            String filePath = "src/main/resources";

            Path file = Paths.get(filePath).resolve("MONITORADORES.pdf");
            Resource resource = (Resource) new UrlResource(file.toUri());


        }
        catch(Exception e){
            e.printStackTrace();
        }

        return null;

    }



}
