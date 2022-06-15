import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPessoaFisica, PessoaFisica } from '../pessoa-fisica.model';
import { PessoaFisicaService } from '../service/pessoa-fisica.service';

@Injectable({ providedIn: 'root' })
export class PessoaFisicaRoutingResolveService implements Resolve<IPessoaFisica> {
  constructor(protected service: PessoaFisicaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPessoaFisica> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((pessoaFisica: HttpResponse<PessoaFisica>) => {
          if (pessoaFisica.body) {
            return of(pessoaFisica.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PessoaFisica());
  }
}
