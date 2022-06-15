import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'pessoa-fisica',
        data: { pageTitle: 'PessoaFisicas' },
        loadChildren: () =>
          import('./pessoaApplication/pessoa-fisica/pessoa-fisica.module').then(m => m.PessoaApplicationPessoaFisicaModule),
      },
      {
        path: 'endereco',
        data: { pageTitle: 'Enderecos' },
        loadChildren: () => import('./pessoaApplication/endereco/endereco.module').then(m => m.PessoaApplicationEnderecoModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
