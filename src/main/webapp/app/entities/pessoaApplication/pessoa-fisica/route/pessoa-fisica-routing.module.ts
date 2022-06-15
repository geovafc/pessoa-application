import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PessoaFisicaComponent } from '../list/pessoa-fisica.component';
import { PessoaFisicaDetailComponent } from '../detail/pessoa-fisica-detail.component';
import { PessoaFisicaUpdateComponent } from '../update/pessoa-fisica-update.component';
import { PessoaFisicaRoutingResolveService } from './pessoa-fisica-routing-resolve.service';

const pessoaFisicaRoute: Routes = [
  {
    path: '',
    component: PessoaFisicaComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PessoaFisicaDetailComponent,
    resolve: {
      pessoaFisica: PessoaFisicaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PessoaFisicaUpdateComponent,
    resolve: {
      pessoaFisica: PessoaFisicaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PessoaFisicaUpdateComponent,
    resolve: {
      pessoaFisica: PessoaFisicaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(pessoaFisicaRoute)],
  exports: [RouterModule],
})
export class PessoaFisicaRoutingModule {}
