import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PessoaFisicaComponent } from './list/pessoa-fisica.component';
import { PessoaFisicaDetailComponent } from './detail/pessoa-fisica-detail.component';
import { PessoaFisicaUpdateComponent } from './update/pessoa-fisica-update.component';
import { PessoaFisicaDeleteDialogComponent } from './delete/pessoa-fisica-delete-dialog.component';
import { PessoaFisicaRoutingModule } from './route/pessoa-fisica-routing.module';

@NgModule({
  imports: [SharedModule, PessoaFisicaRoutingModule],
  declarations: [PessoaFisicaComponent, PessoaFisicaDetailComponent, PessoaFisicaUpdateComponent, PessoaFisicaDeleteDialogComponent],
  entryComponents: [PessoaFisicaDeleteDialogComponent],
})
export class PessoaApplicationPessoaFisicaModule {}
