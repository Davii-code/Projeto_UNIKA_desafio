package com.projetounika;

import com.projetounika.JsCodigo.Mask;
import com.projetounika.JsCodigo.ValidacaoInput;
import com.projetounika.entities.Endereco;
import com.projetounika.services.EnderecoHttpClient;
import com.projetounika.services.MonitoradorHttpClient;
import lombok.SneakyThrows;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;


import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Collections;
import java.util.List;


public class Monitorador extends WebPage {


    public Monitorador() {
        MonitoradorHttpClient monitoradorHttpClient = new MonitoradorHttpClient("http://localhost:8080/monitorador");
        Endereco endereco = new Endereco();


        //Modal
        final ModalWindow modal = new ModalWindow("modal");
        modal.setInitialHeight(600);
        modal.setInitialWidth(800);
        add(modal);

        //Modal
        final ModalWindow modalImport = new ModalWindow("modalImport");
        modalImport.setInitialHeight(250);
        modalImport.setInitialWidth(550);
        add(modalImport);

        // Obtém a lista de monitoradores usando o HttpClient
        List<com.projetounika.entities.Monitorador> list = monitoradorHttpClient.listarTodos();

        // Cria um modelo para a lista usando CompoundPropertyModel
        final CompoundPropertyModel<List<com.projetounika.entities.Monitorador>> monitoradorListModel = new CompoundPropertyModel<>(list);

        final ListView<com.projetounika.entities.Monitorador> monitoradorListView = new ListView<com.projetounika.entities.Monitorador>("monitorador", monitoradorListModel) {
            @Override
            protected void populateItem(ListItem<com.projetounika.entities.Monitorador> listItem) {
                final com.projetounika.entities.Monitorador monitorador = listItem.getModelObject();
                listItem.add(new Label("id", monitorador.getId()));
                listItem.add(new Label("nome", monitorador.getNome()));
                listItem.add(new Label("CPF", monitorador.getCpf()));
                listItem.add(new Label("CNPJ", monitorador.getCnpj() != null ? formatarCNPJ(monitorador.getCnpj()) : ""));
                listItem.add(new Label("ativo", monitorador.isAtivo() ? "Sim" : "Não"));


                EnderecoHttpClient enderecoHttpClient = new EnderecoHttpClient("http://localhost:8080/monitorador/" + monitorador.getId() + "/enderecos");
                List<Endereco> enderecoList = enderecoHttpClient.listarTodos();
                final CompoundPropertyModel<List<Endereco>> enderecoListModel = new CompoundPropertyModel<>(enderecoList);
                listItem.add(new AjaxLink<Void>("editarLink") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {

                        modal.setContent(new DetalhesMonitorador(listItem.getModelObject(), modal.getContentId(), modal));
                        modal.show(target);

                    }

                });
                listItem.add(new AjaxLink<Void>("editarLinkEndereco") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        modal.setInitialWidth (900);
                        modal.setInitialHeight (408);
                        modal.setContent(new DetalhesEndereco(enderecoListModel, modal.getContentId(), modal, monitorador.getId()));
                        modal.show(target);

                    }

                });


                listItem.add(new AjaxLink<Void>("LinkDeletar") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        modal.setInitialWidth (1143);
                        modal.setInitialHeight (583);
                        modal.setContent(new DeleteMonitorador(monitorador, modal.getContentId(), modal));
                        modal.show(target);

                    }

                });

            }
        };
        modal.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
            @Override
            public void onClose(AjaxRequestTarget ajaxRequestTarget) {
                setResponsePage(Monitorador.class);
            }
        });
        add(monitoradorListView);




        AjaxLink<Void> linkCriarPessoaJuridica = new AjaxLink<Void>("linkCriarJ") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                modal.setContent(new CadastroMonitorador(modal.getContentId(), modal, "Juridica"));
                modal.show(ajaxRequestTarget);
            }
        };


        AjaxLink<Void> linkCriarPessoaFisica = new AjaxLink<Void>("linkCriarF") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                modal.setContent(new CadastroMonitorador(modal.getContentId(), modal, "Fisica"));
                modal.show(ajaxRequestTarget);

            }
        };
        AjaxLink<Void>ImportArquivo = new AjaxLink<Void>("importExcel") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {

                modalImport.setContent(new ImportArquivo(modalImport.getContentId()));
                modalImport.show(ajaxRequestTarget);
            }
        };
        ExternalLink linkExcel = new ExternalLink("excel", "http://localhost:8080/monitorador/relatorio/excel");
        ExternalLink linkPdf = new ExternalLink("pdf", "http://localhost:8080/monitorador/relatorio/pdfs");
        add(linkPdf);
        final TextField<String> codigo = new TextField<>("idF", Model.of(""));
        final TextField<String> nome = new TextField<>("nomeF",Model.of(""));
        final TextField<String> cpf = new TextField<>("cpfF",Model.of(""));
        cpf.add (new Mask ("000.000.000-00"));

        final TextField<String> Cnpj = new TextField<>("cnpjF",Model.of(""));
        Cnpj.add (new Mask ("00.000.000/0000-00"));

        String url;




        Form<com.projetounika.entities.Monitorador> form = new Form<>("filtro"){
            @SneakyThrows
            @Override
            protected void onSubmit() {
                String codigoValue = codigo.getModelObject();
                String nomeValue = nome.getModelObject();
                String cpfValue = cpf.getModelObject();
                String CnpjValue = Cnpj.getModelObject();

                if (nomeValue != null){
                    String encodedNome = URLEncoder.encode(nomeValue, StandardCharsets.UTF_8.toString ());
                    encodedNome = encodedNome.replace("+", "%20");
                    MonitoradorHttpClient monitoradorHttpClient1 = new MonitoradorHttpClient("http://localhost:8080/monitorador/filtroNome/" + encodedNome);
                    List<com.projetounika.entities.Monitorador> list1 = monitoradorHttpClient1.listarTodos();
                    final CompoundPropertyModel<List<com.projetounika.entities.Monitorador>>listModel = new CompoundPropertyModel<>(list1);
                    monitoradorListView.setModel(listModel);
                    linkPdf.setDefaultModelObject("http://localhost:8080/monitorador/relatorio/pdfs?nome=" + nomeValue);
                    linkExcel.setDefaultModelObject("http://localhost:8080/monitorador/relatorio/excel?nome="+nomeValue);
                }else {if (codigoValue != null){
                    MonitoradorHttpClient monitoradorHttpClient2 = new MonitoradorHttpClient("http://localhost:8080/monitorador/" + codigoValue);
                    com.projetounika.entities.Monitorador monitorador = monitoradorHttpClient2.listarPorID();
                    if (monitorador != null) {
                        // Cria uma lista com um único elemento (o monitorador encontrado)
                        List<com.projetounika.entities.Monitorador> list2 = Collections.singletonList(monitorador);
                        final CompoundPropertyModel<List<com.projetounika.entities.Monitorador>> listModel = new CompoundPropertyModel<>(list2);
                        monitoradorListView.setModel(listModel);
                        linkPdf.setDefaultModelObject("http://localhost:8080/monitorador/relatorio/pdfs?id=" + codigoValue);
                        linkExcel.setDefaultModelObject("http://localhost:8080/monitorador/relatorio/excel?id="+codigoValue);
                    }
                } else if (cpfValue != null){
                    String cpfValueFormatado = formatarCPF(cpfValue);
                    MonitoradorHttpClient monitoradorHttpClient3 = new MonitoradorHttpClient("http://localhost:8080/monitorador/filtroCpf/" + cpfValueFormatado);
                    com.projetounika.entities.Monitorador monitorador = monitoradorHttpClient3.listarPorID();
                    if (monitorador != null) {
                        // Cria uma lista com um único elemento (o monitorador encontrado)
                        List<com.projetounika.entities.Monitorador> list3 = Collections.singletonList(monitorador);
                        final CompoundPropertyModel<List<com.projetounika.entities.Monitorador>> listModel = new CompoundPropertyModel<>(list3);
                        monitoradorListView.setModel(listModel);
                        linkPdf.setDefaultModelObject("http://localhost:8080/monitorador/relatorio/pdfs?cpf=" + cpfValueFormatado);
                        linkExcel.setDefaultModelObject("http://localhost:8080/monitorador/relatorio/excel?cpf="+cpfValueFormatado);
                    }
                }


                }

                if (CnpjValue != null) {
                    String cnpjFormatado = CnpjValue;
                    cnpjFormatado = cnpjFormatado.replaceAll("\\D", "");
                    MonitoradorHttpClient monitoradorHttpClient4 = new MonitoradorHttpClient("http://localhost:8080/monitorador/filtroCnpj/" + cnpjFormatado);
                    com.projetounika.entities.Monitorador monitorador = monitoradorHttpClient4.listarPorID();
                    if (monitorador != null) {
                        // Cria uma lista com um único elemento (o monitorador encontrado)
                        List<com.projetounika.entities.Monitorador> list4 = Collections.singletonList(monitorador);
                        final CompoundPropertyModel<List<com.projetounika.entities.Monitorador>> listModel = new CompoundPropertyModel<>(list4);
                        monitoradorListView.setModel(listModel);
                        linkPdf.setDefaultModelObject("http://localhost:8080/monitorador/relatorio/pdfs?cnpj=" + cnpjFormatado);
                        linkExcel.setDefaultModelObject("http://localhost:8080/monitorador/relatorio/excel?cnpj="+cnpjFormatado);
                    }
                }

                if (CnpjValue==null && cpfValue == null && codigoValue == null && nomeValue ==null){
                    monitoradorListView.setModel(monitoradorListModel);
                    linkPdf.setDefaultModelObject("http://localhost:8080/monitorador/relatorio/pdfs");
                    linkExcel.setDefaultModelObject("http://localhost:8080/monitorador/relatorio/excel");
                }



            }
        };



        form.add(codigo);
        form.add(nome);
        form.add(cpf);
        form.add(Cnpj);
        add(linkExcel);

        add(linkCriarPessoaFisica);
        add(linkCriarPessoaJuridica);
        add(form);
        add(ImportArquivo);


    }

    public static String formatarCPF(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");
        // Verificar se o CPF tem 11 dígitos
        if (cpf.length() != 11) {

        }

        // Formatando o CPF com pontos e traço
        return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." +
                cpf.substring(6, 9) + "-" + cpf.substring(9, 11);
    }


    public static String formatarCNPJ(String cnpj) {

        cnpj = cnpj.replaceAll("\\D", "");
        if (cnpj.length() != 14) {
            throw new IllegalArgumentException("O Cnpj deve conter 14 dígitos numéricos.");
        }


        return cnpj.substring(0, 2) + "." + cnpj.substring(2, 5) + "." +
                cnpj.substring(5, 8) + "/" + cnpj.substring(8, 12) + "-" + cnpj.substring(12,14);
    }
}



