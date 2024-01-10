package com.projetounika;

import com.projetounika.entities.Monitorador;
import com.projetounika.services.MonitoradorHttpClient;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.file.File;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ImportArquivo extends Panel {
    public ImportArquivo(String id) {
        super(id);
        MonitoradorHttpClient monitoradorHttpClient = new MonitoradorHttpClient("http://localhost:8080/monitorador");
        FileUploadField fileUploadField = new FileUploadField("fileUploadField");
        Form<Void> form = new Form<>("form") {
            @Override
            protected void onSubmit() {
                FileUpload fileUpload = fileUploadField.getFileUpload();

                try {
                    File file = new File("src/main/resources/" +fileUpload.getClientFileName());
                    System.out.println (fileUpload.getClientFileName ());
                    fileUpload.writeTo(file);
                    List<Monitorador> moni = monitoradorHttpClient.criarExcel(file);
                    info("Upload completed!");
                } catch (Exception e) {
                    e.printStackTrace();
                    error("Upload failed!");
                }


            }
        };

        ExternalLink linkModelo = new ExternalLink("baixarModelo", "http://localhost:8080/monitorador/relatorio/excelModelo");
        form.add(fileUploadField);
        add (linkModelo);
        add(form);

    }
}
