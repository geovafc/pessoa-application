import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPessoaFisica } from '../pessoa-fisica.model';

@Component({
  selector: 'jhi-pessoa-fisica-detail',
  templateUrl: './pessoa-fisica-detail.component.html',
})
export class PessoaFisicaDetailComponent implements OnInit {
  pessoaFisica: IPessoaFisica | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pessoaFisica }) => {
      this.pessoaFisica = pessoaFisica;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
