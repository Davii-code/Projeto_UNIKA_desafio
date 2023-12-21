package com.projetounika;

import com.projetounika.entities.Endereco;
import org.apache.wicket.extensions.markup.html.form.select.Select;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.Arrays;
import java.util.List;

public class DetalhesEndereco extends WebPage {

    DetalhesEndereco(){

    }
    public DetalhesEndereco(Endereco endereco) {
        List<String> Op = Arrays.asList("Sim", "Não");
        IModel<Endereco>enderecoIModel = new CompoundPropertyModel<>(endereco);
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
        form.add(new Select<>("estado"));
        form.add(new Select<>("principal", new DropDownChoice<String>("op") {
            @Override
            public String getObject() {
                String s = "Sim";
                String n = "Não";
                return endereco.getPrincipal() ? s : n;
            }

        }).add(new DropDownChoice<String>("op", new Model(), Op)));

        add(form);

    }

}
