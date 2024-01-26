export interface Endereco {
  endereco: string;
  numero: string;
  cep: string;
  telefone: string;
  bairro: string;
  cidade: string;
  estado: string;
  principal: boolean;
}

export interface monitorador extends Array<Endereco>{}

