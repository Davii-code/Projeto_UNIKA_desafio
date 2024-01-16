package com.projetounika;

import com.projetounika.JsCodigo.Mask;
import com.projetounika.JsCodigo.ValidacaoInput;
import com.projetounika.entities.Endereco;
import com.projetounika.entities.Monitorador;

import com.projetounika.services.MonitoradorHttpClient;
import lombok.SneakyThrows;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.EmailAddressValidator;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class DetalhesMonitorador extends Panel {


    public DetalhesMonitorador(Monitorador monitorador, String id, ModalWindow modalWindow) {
        super(id);
        Endereco endereco = new Endereco();

        IModel<Monitorador> monitoradorIModel = new CompoundPropertyModel<>(monitorador);
        MonitoradorHttpClient monitoradorHttpClient = new MonitoradorHttpClient("http://localhost:8080/monitorador");
        final ModalWindow modal = new ModalWindow ("modal");
        modal.setInitialHeight (120);
        modal.setInitialWidth (350);
        add (modal);
        Validacao validacao = new Validacao ();

        DropDownChoice<String> escolheTipo = new DropDownChoice<>("escolheTipo",
                Model.of(monitorador.getTipo()),
                List.of("Física", "Juridica"));

        DropDownChoice<String> escolheAtivo = new DropDownChoice<>("escolheAtivo",
                Model.of(monitorador.isAtivo() ? "Sim" : "Não"),
                List.of("Sim", "Não"));


        AjaxButton editar = new AjaxButton("EditarSubmit") {
            @SneakyThrows
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                super.onSubmit(target);
                String cnpjFormatado = monitorador.getCnpj();

                String valorAtivo = escolheAtivo.getModelObject();
                try {
                    monitorador.setAtivo(valorAtivo.equals ("Sim"));

                    if (monitorador.getTipo().equals("Fisica")) {
                        String cpf = monitorador.getCpf();
                        String cpfFormatado = formatarCPF(cpf);
                        monitorador.setCpf(cpfFormatado);
                        String dataNascimento = monitorador.getData_nascimento();
                        if (!dataNascimento.contains("/") && dataNascimento.length() == 8) {
                            String dataFormatada = formatarDataComBarra(dataNascimento);
                            monitorador.setData_nascimento(dataFormatada);
                        }
                    } else if (monitorador.getTipo().equals("Juridica")){

                        cnpjFormatado = cnpjFormatado.replaceAll("\\D", "");
                        monitorador.setCnpj(cnpjFormatado);
                    }

                    if (monitorador.getCpf () != null) {
                        if (!validacao.isCPF(monitorador.getCpf ())) {
                            throw new Exception("Erro ao editar, verificar o CPF");
                        }
                    }

                    // Validar CNPJ
                    if (cnpjFormatado != null) {
                        if (Validacao.isCNPJ (cnpjFormatado)) {
                            throw new Exception("Erro ao editar, verificar o CNPJ");
                        }
                    }

                    // Continue com a atualização apenas se as validações passarem
                    monitoradorHttpClient.Atualizar(monitorador);
                    modal.setContent(new MenssagemFed(modal.getContentId(), true, "Editado com Sucesso"));
                    modal.show(target);
                    modal.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
                        @Override
                        public void onClose(AjaxRequestTarget ajaxRequestTarget) {
                            setResponsePage(com.projetounika.Monitorador.class);
                        }
                    });
                } catch (Exception e) {
                    modal.setContent(new MenssagemFed(modal.getContentId(), false, "Erro ao editar: " + e.getMessage()));
                    modal.show(target);
                }

            }
        };

        Form<Monitorador> form = new Form<>("edit", monitoradorIModel) {};
        form.add (editar);
        add(form);

        final TextField<String> nome = new TextField<>("nome");
        final TextField<String> cpf = new TextField<>("cpf");
        cpf.add (new Mask ("000.000.000-00"));
        final TextField<String> cnpj = new TextField<>("cnpj",Model.of(formatarCNPJ(monitorador.getCnpj())));
        cnpj.add (new Mask ("00.000.000/0000-00"));
        final TextField<String> email = new TextField<>("email");
        email.add (EmailAddressValidator.getInstance ());
        final TextField<String> rg = new TextField<>("rg");
        rg.add (new ValidacaoInput ());
        final TextField<String> data = new TextField<>("Data_nascimento");
        data.add (new Mask ("00/00/0000"));
        final TextField<String> inscricao = new TextField<>("inscricao");
        inscricao.add (new ValidacaoInput ());

        //Label
        final Label IE = new Label("ins", "Inscrição Estadual");
        final Label RG = new Label("RG", "RG");
        final Label date = new Label("date", "Data de Nascimento");

        if (monitorador.getTipo().equals("Fisica")) {
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


    }

    private String formatarDataComBarra(String dataNascimento) {
        try {
            DateFormat formatoEntrada = new SimpleDateFormat("ddMMyyyy");
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

        if (cnpj.length() != 14) {
            throw new IllegalArgumentException("O Cnpj deve conter 14 dígitos numéricos.");
        }

        // Formatando o CPF com pontos e traço
        return cnpj.substring(0, 2) + "." + cnpj.substring(2, 5) + "." +
                cnpj.substring(5, 8) + "/" + cnpj.substring(8, 12) + "-" + cnpj.substring(12,14);
    }
}







