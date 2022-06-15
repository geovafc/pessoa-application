import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IEndereco, Endereco } from '../endereco.model';
import { EnderecoService } from '../service/endereco.service';
import { IPessoaFisica } from 'app/entities/pessoaApplication/pessoa-fisica/pessoa-fisica.model';
import { PessoaFisicaService } from 'app/entities/pessoaApplication/pessoa-fisica/service/pessoa-fisica.service';

@Component({
  selector: 'jhi-endereco-update',
  templateUrl: './endereco-update.component.html',
})
export class EnderecoUpdateComponent implements OnInit {
  isSaving = false;

  pessoaFisicasSharedCollection: IPessoaFisica[] = [];

  editForm = this.fb.group({
    id: [],
    logradouro: [null, [Validators.required]],
    cep: [null, [Validators.required]],
    cidade: [null, [Validators.required]],
    estado: [null, [Validators.required]],
    pessoaFisica: [null, Validators.required],
  });

  constructor(
    protected enderecoService: EnderecoService,
    protected pessoaFisicaService: PessoaFisicaService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ endereco }) => {
      this.updateForm(endereco);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const endereco = this.createFromForm();
    if (endereco.id !== undefined) {
      this.subscribeToSaveResponse(this.enderecoService.update(endereco));
    } else {
      this.subscribeToSaveResponse(this.enderecoService.create(endereco));
    }
  }

  trackPessoaFisicaById(_index: number, item: IPessoaFisica): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEndereco>>): void {
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

  protected updateForm(endereco: IEndereco): void {
    this.editForm.patchValue({
      id: endereco.id,
      logradouro: endereco.logradouro,
      cep: endereco.cep,
      cidade: endereco.cidade,
      estado: endereco.estado,
      pessoaFisica: endereco.pessoaFisica,
    });

    this.pessoaFisicasSharedCollection = this.pessoaFisicaService.addPessoaFisicaToCollectionIfMissing(
      this.pessoaFisicasSharedCollection,
      endereco.pessoaFisica
    );
  }

  protected loadRelationshipsOptions(): void {
    this.pessoaFisicaService
      .query()
      .pipe(map((res: HttpResponse<IPessoaFisica[]>) => res.body ?? []))
      .pipe(
        map((pessoaFisicas: IPessoaFisica[]) =>
          this.pessoaFisicaService.addPessoaFisicaToCollectionIfMissing(pessoaFisicas, this.editForm.get('pessoaFisica')!.value)
        )
      )
      .subscribe((pessoaFisicas: IPessoaFisica[]) => (this.pessoaFisicasSharedCollection = pessoaFisicas));
  }

  protected createFromForm(): IEndereco {
    return {
      ...new Endereco(),
      id: this.editForm.get(['id'])!.value,
      logradouro: this.editForm.get(['logradouro'])!.value,
      cep: this.editForm.get(['cep'])!.value,
      cidade: this.editForm.get(['cidade'])!.value,
      estado: this.editForm.get(['estado'])!.value,
      pessoaFisica: this.editForm.get(['pessoaFisica'])!.value,
    };
  }
}
