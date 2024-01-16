package com.projetounika;

import com.projetounika.entities.Monitorador;
import com.projetounika.services.MonitoradorHttpClient;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
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
        final ModalWindow modal = new ModalWindow ("modal");
        modal.setInitialHeight (120);
        modal.setInitialWidth (350);
        add (modal);


        AjaxButton submitImport = new AjaxButton ("submit") {
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                super.onSubmit (target);
                FileUpload fileUpload = fileUploadField.getFileUpload();

                try {
                    File file = new File("src/main/resources/" +fileUpload.getClientFileName());
                    fileUpload.writeTo(file);
                    List<Monitorador> moni = monitoradorHttpClient.criarExcel(file);
                    modal.setContent(new MenssagemFed(modal.getContentId(), true, "importado com Sucesso"));
                    modal.show(target);
                    modal.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
                        @Override
                        public void onClose(AjaxRequestTarget ajaxRequestTarget) {
                            setResponsePage(com.projetounika.Monitorador.class);
                        }
                    });
                } catch (Exception e) {
                    modal.setContent(new MenssagemFed(modal.getContentId(), false, "Erro ao Importar: " + e.getMessage()));
                    modal.show(target);
                }



            }
        };
        Form<Void> form = new Form<>("form") {};

        ExternalLink linkModelo = new ExternalLink("baixarModelo", "http://localhost:8080/monitorador/relatorio/excelModelo");
        form.add (submitImport);
        form.add(fileUploadField);
        add (linkModelo);
        add(form);

    }
}
