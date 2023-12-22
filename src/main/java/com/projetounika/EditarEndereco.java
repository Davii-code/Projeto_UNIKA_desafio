package com.projetounika;

import com.projetounika.entities.Endereco;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.List;

public class EditarEndereco extends Panel {
    public EditarEndereco(Endereco endereco, String id, ModalWindow modalWindow) {
        super(id);
        IModel<Endereco> enderecoIModel = new CompoundPropertyModel<>(endereco);
        Form<Endereco> form = new Form<>("edit", enderecoIModel){
            @Override
            protected void onSubmit(){

            }

        };
        form.add(new TextField<>("id"));
        form.add(new TextField<>("endereco"));
        form.add(new TextField<>("cep"));
        form.add(new TextField<>("numero"));
        form.add(new TextField<>("telefone"));
        form.add(new TextField<>("cidade"));
        DropDownChoice<String> escolha = new DropDownChoice<>("principal",
                Model.of(endereco.getPrincipal() ? "Sim" : "Não"), // Valor padrão
                List.of("Sim", "Não"));


        DropDownChoice<String> escolhaEstado = new DropDownChoice<>("estado",
                Model.of(endereco.getEstado()),
                List.of("AC", "AL", "AP", "AM", "BA", "CE", "DF",
                        "ES", "GO", "MA", "MT", "MS",
                        "MG", "PA", "PB", "PR", "PE", "PI", "RJ",
                        "RN", "RS", "RO", "RR", "SC",
                        "SP", "SE", "TO"));

        form.add(escolha);
        form.add(escolhaEstado);
        add(form);

//        "Acre", "Alagoas", "Amapá", "Amazonas", "Bahia", "Ceará", "Distrito Federal",
//                "Espírito Santo", "Goiás", "Maranhão", "Mato Grosso", "Mato Grosso do Sul",
//                "Minas Gerais", "Pará", "Paraíba", "Paraná", "Pernambuco", "Piauí", "Rio de Janeiro",
//                "Rio Grande do Norte", "Rio Grande do Sul", "Rondônia", "Roraima", "Santa Catarina",
//                "São Paulo", "Sergipe", "Tocantins")




    }
}
