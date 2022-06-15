import { IPessoaFisica } from 'app/entities/pessoaApplication/pessoa-fisica/pessoa-fisica.model';

export interface IEndereco {
  id?: number;
  logradouro?: string;
  cep?: string;
  cidade?: string;
  estado?: string;
  pessoaFisica?: IPessoaFisica;
}

export class Endereco implements IEndereco {
  constructor(
    public id?: number,
    public logradouro?: string,
    public cep?: string,
    public cidade?: string,
    public estado?: string,
    public pessoaFisica?: IPessoaFisica
  ) {}
}

export function getEnderecoIdentifier(endereco: IEndereco): number | undefined {
  return endereco.id;
}
