import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IPessoaFisica, PessoaFisica } from '../pessoa-fisica.model';
import { PessoaFisicaService } from '../service/pessoa-fisica.service';

@Component({
  selector: 'jhi-pessoa-fisica-update',
  templateUrl: './pessoa-fisica-update.component.html',
})
export class PessoaFisicaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    cpf: [null, [Validators.required]],
    idade: [null, [Validators.required]],
    email: [null, [Validators.required]],
    telefone: [null, [Validators.required]],
  });

  constructor(protected pessoaFisicaService: PessoaFisicaService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pessoaFisica }) => {
      this.updateForm(pessoaFisica);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pessoaFisica = this.createFromForm();
    if (pessoaFisica.id !== undefined) {
      this.subscribeToSaveResponse(this.pessoaFisicaService.update(pessoaFisica));
    } else {
      this.subscribeToSaveResponse(this.pessoaFisicaService.create(pessoaFisica));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPessoaFisica>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(pessoaFisica: IPessoaFisica): void {
    this.editForm.patchValue({
      id: pessoaFisica.id,
      nome: pessoaFisica.nome,
      cpf: pessoaFisica.cpf,
      idade: pessoaFisica.idade,
      email: pessoaFisica.email,
      telefone: pessoaFisica.telefone,
    });
  }

  protected createFromForm(): IPessoaFisica {
    return {
      ...new PessoaFisica(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      cpf: this.editForm.get(['cpf'])!.value,
      idade: this.editForm.get(['idade'])!.value,
      email: this.editForm.get(['email'])!.value,
      telefone: this.editForm.get(['telefone'])!.value,
    };
  }
}
