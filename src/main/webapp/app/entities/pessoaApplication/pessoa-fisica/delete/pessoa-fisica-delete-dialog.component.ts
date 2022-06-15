import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPessoaFisica } from '../pessoa-fisica.model';
import { PessoaFisicaService } from '../service/pessoa-fisica.service';

@Component({
  templateUrl: './pessoa-fisica-delete-dialog.component.html',
})
export class PessoaFisicaDeleteDialogComponent {
  pessoaFisica?: IPessoaFisica;

  constructor(protected pessoaFisicaService: PessoaFisicaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pessoaFisicaService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
