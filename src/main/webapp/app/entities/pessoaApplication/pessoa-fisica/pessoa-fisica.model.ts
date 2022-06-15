import { IEndereco } from 'app/entities/pessoaApplication/endereco/endereco.model';

export interface IPessoaFisica {
  id?: number;
  nome?: string;
  cpf?: string;
  idade?: string;
  email?: string;
  telefone?: string;
  enderecos?: IEndereco[] | null;
}

export class PessoaFisica implements IPessoaFisica {
  constructor(
    public id?: number,
    public nome?: string,
    public cpf?: string,
    public idade?: string,
    public email?: string,
    public telefone?: string,
    public enderecos?: IEndereco[] | null
  ) {}
}

export function getPessoaFisicaIdentifier(pessoaFisica: IPessoaFisica): number | undefined {
  return pessoaFisica.id;
}
