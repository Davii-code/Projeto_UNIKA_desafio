package com.projetounika;

import com.projetounika.entities.Monitorador;
import com.projetounika.services.MonitoradorHttpClient;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.markup.html.panel.Panel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;


public class DeleteMonitorador extends Panel {
    public DeleteMonitorador(Monitorador monitorador, String id, ModalWindow modalWindow) {
        super(id);

        MonitoradorHttpClient monitoradorHttpClient = new MonitoradorHttpClient("http://localhost:8080/monitorador");
        IModel<Monitorador> monitoradorIModel = new CompoundPropertyModel<>(monitorador);
        DropDownChoice<String> escolheTipo = new DropDownChoice<>("escolheTipo",
                Model.of(monitorador.getTipo()),
                List.of("Física", "Juridica"));

        DropDownChoice<String> escolheAtivo = new DropDownChoice<>("escolheAtivo",
                Model.of(monitorador.isAtivo() ? "Sim" : "Não"),
                List.of("Sim", "Não"));

        final ModalWindow modal = new ModalWindow ("modal");
        modal.setInitialHeight (120);
        modal.setInitialWidth (350);
        add (modal);
        Form<Monitorador> form = new Form<>("edit", monitoradorIModel) {};
        AjaxButton submitdeletar = new AjaxButton ("submit") {
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                super.onSubmit (target);
                try{

                    monitoradorHttpClient.deletar(monitorador.getId());
                    modal.setContent(new MenssagemFed(modal.getContentId(), true, "Apagado com Sucesso"));
                    modal.show(target);
                    modal.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
                        @Override
                        public void onClose(AjaxRequestTarget ajaxRequestTarget) {
                            setResponsePage(com.projetounika.Monitorador.class);
                        }
                    });
                }catch (Exception e){

                    modal.setContent(new MenssagemFed(modal.getContentId(), false, "Erro ao Apagar: " + e.getMessage()));
                    modal.show(target);
                }

            }
        };
        add(form);
        final TextField<String> codigo = new TextField<>("id");
        final TextField<String> nome = new TextField<>("nome");
        final TextField<String> cpf = new TextField<>("cpf");
        final TextField<String> cnpj = new TextField<>("cnpj",Model.of(formatarCNPJ(monitorador.getCnpj())));
        final TextField<String> email = new TextField<>("email");
        final TextField<String> rg = new TextField<>("rg");
        final TextField<String> data = new TextField<>("Data_nascimento");
        final TextField<String> inscricao = new TextField<>("inscricao");
        final Label IE = new Label("ins", "Inscrição Estadual");
        final Label RG = new Label("RG", "RG");
        final Label date = new Label("date", "Data de Nascimento");


        if (monitorador.getTipo().equals("Fisica")) {
            codigo.setVisible (true);
            codigo.setOutputMarkupPlaceholderTag (true);
            cpf.setVisible(true);
            cpf.setOutputMarkupPlaceholderTag(true);
            rg.setVisible(true);
            rg.setOutputMarkupPlaceholderTag(true);
            data.setVisible(true);
            data.setOutputMarkupPlaceholderTag(true);
            cnpj.setVisible(false);
            cnpj.setOutputMarkupPlaceholderTag(true);
            inscricao.setVisible(false);
            inscricao.setOutputMarkupPlaceholderTag(true);
            IE.setVisible(false);
            IE.setOutputMarkupPlaceholderTag(true);
            RG.setVisible(true);
            RG.setOutputMarkupPlaceholderTag(true);
            date.setVisible(true);
            date.setOutputMarkupPlaceholderTag(true);
        } else {
            if (monitorador.getTipo().equals("Juridica")) {

                codigo.setVisible (true);
                codigo.setOutputMarkupPlaceholderTag (true);
                cnpj.setVisible(true);
                cnpj.setOutputMarkupPlaceholderTag(true);
                inscricao.setVisible(true);
                inscricao.setOutputMarkupPlaceholderTag(true);
                IE.setVisible(true);
                IE.setOutputMarkupPlaceholderTag(true);
                RG.setVisible(false);
                RG.setOutputMarkupPlaceholderTag(true);
                cpf.setVisible(false);
                cpf.setOutputMarkupPlaceholderTag(true);
                rg.setVisible(false);
                rg.setOutputMarkupPlaceholderTag(true);
                data.setVisible(false);
                data.setOutputMarkupPlaceholderTag(true);
                date.setVisible(false);
                date.setOutputMarkupPlaceholderTag(true);
            }
    }
        form.add(codigo);
        form.add(nome);
        form.add(cpf);
        form.add(cnpj);
        form.add(email);
        form.add(rg);
        form.add(data);
        form.add(inscricao);
        form.add(IE);
        form.add(date);
        form.add(RG);
        form.add(escolheTipo);
        form.add(escolheAtivo);
        form.add (submitdeletar);



}

    private String formatarDataComBarra(String dataNascimento) {
        try {
            DateFormat formatoEntrada = new SimpleDateFormat ("ddMMyyyy");
            DateFormat formatoSaida = new SimpleDateFormat("dd/MM/yyyy");
            return formatoSaida.format(formatoEntrada.parse(dataNascimento));
        } catch (ParseException e) {
            throw new RuntimeException("Erro ao formatar a data.", e);
        }
    }

    public static String formatarCPF(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");
        // Verificar se o CPF tem 11 dígitos
        if (cpf.length() != 11) {
            throw new IllegalArgumentException("O CPF deve conter 11 dígitos numéricos.");
        }

        // Formatando o CPF com pontos e traço
        return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." +
                cpf.substring(6, 9) + "-" + cpf.substring(9, 11);
    }

    public static String formatarCNPJ(String cnpj) {
        if (cnpj!=null){
            cnpj = cnpj.replaceAll("\\D", "");
        }
        if (cnpj == null){
            return null;
        }
        // Verificar se o CPF tem 11 dígitos
        if (cnpj.length() != 14) {
            throw new IllegalArgumentException("O Cnpj deve conter 14 dígitos numéricos.");
        }

        // Formatando o CPF com pontos e traço
        return cnpj.substring(0, 2) + "." + cnpj.substring(2, 5) + "." +
                cnpj.substring(5, 8) + "/" + cnpj.substring(8, 12) + "-" + cnpj.substring(12,14);
    }
}