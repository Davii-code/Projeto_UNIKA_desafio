import {Endereco} from "./endereco.models";

export interface MonitoradorModels{
  id: number;
  nome: string;
  cpf: string;
  cnpj: string;
  email: string;
  rg: string;
  inscricao: string;
  data_nascimento: string;
  ativo: boolean;
  tipo:string;
  endereco: Endereco;

}

export interface monitorador extends Array<MonitoradorModels>{}

