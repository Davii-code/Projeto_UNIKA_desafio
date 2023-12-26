package com.projetounika;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.WebPage;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		super(parameters);

	Link<Void> linkPessoaFisica = new Link<Void>("PessoaFisica") {
		@Override
		public void onClick() {
			setResponsePage(MonitoradorFisica.class);
		}
	};

		Link<Void> linkPessoaJuridica = new Link<Void>("PessoaJuridica") {
			@Override
			public void onClick() {
				setResponsePage(MonitoradorJuridica.class);
			}
		};

		add(linkPessoaFisica);
		add(linkPessoaJuridica);

    }
}
