import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPessoaFisica, getPessoaFisicaIdentifier } from '../pessoa-fisica.model';

export type EntityResponseType = HttpResponse<IPessoaFisica>;
export type EntityArrayResponseType = HttpResponse<IPessoaFisica[]>;

@Injectable({ providedIn: 'root' })
export class PessoaFisicaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/pessoa-fisicas', 'pessoaapplication');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(pessoaFisica: IPessoaFisica): Observable<EntityResponseType> {
    return this.http.post<IPessoaFisica>(this.resourceUrl, pessoaFisica, { observe: 'response' });
  }

  update(pessoaFisica: IPessoaFisica): Observable<EntityResponseType> {
    return this.http.put<IPessoaFisica>(`${this.resourceUrl}/${getPessoaFisicaIdentifier(pessoaFisica) as number}`, pessoaFisica, {
      observe: 'response',
    });
  }

  partialUpdate(pessoaFisica: IPessoaFisica): Observable<EntityResponseType> {
    return this.http.patch<IPessoaFisica>(`${this.resourceUrl}/${getPessoaFisicaIdentifier(pessoaFisica) as number}`, pessoaFisica, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPessoaFisica>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPessoaFisica[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPessoaFisicaToCollectionIfMissing(
    pessoaFisicaCollection: IPessoaFisica[],
    ...pessoaFisicasToCheck: (IPessoaFisica | null | undefined)[]
  ): IPessoaFisica[] {
    const pessoaFisicas: IPessoaFisica[] = pessoaFisicasToCheck.filter(isPresent);
    if (pessoaFisicas.length > 0) {
      const pessoaFisicaCollectionIdentifiers = pessoaFisicaCollection.map(
        pessoaFisicaItem => getPessoaFisicaIdentifier(pessoaFisicaItem)!
      );
      const pessoaFisicasToAdd = pessoaFisicas.filter(pessoaFisicaItem => {
        const pessoaFisicaIdentifier = getPessoaFisicaIdentifier(pessoaFisicaItem);
        if (pessoaFisicaIdentifier == null || pessoaFisicaCollectionIdentifiers.includes(pessoaFisicaIdentifier)) {
          return false;
        }
        pessoaFisicaCollectionIdentifiers.push(pessoaFisicaIdentifier);
        return true;
      });
      return [...pessoaFisicasToAdd, ...pessoaFisicaCollection];
    }
    return pessoaFisicaCollection;
  }
}
