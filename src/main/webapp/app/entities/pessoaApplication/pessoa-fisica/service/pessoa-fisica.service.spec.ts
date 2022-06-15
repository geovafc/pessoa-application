import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPessoaFisica, PessoaFisica } from '../pessoa-fisica.model';

import { PessoaFisicaService } from './pessoa-fisica.service';

describe('PessoaFisica Service', () => {
  let service: PessoaFisicaService;
  let httpMock: HttpTestingController;
  let elemDefault: IPessoaFisica;
  let expectedResult: IPessoaFisica | IPessoaFisica[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PessoaFisicaService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      nome: 'AAAAAAA',
      cpf: 'AAAAAAA',
      idade: 'AAAAAAA',
      email: 'AAAAAAA',
      telefone: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a PessoaFisica', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new PessoaFisica()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PessoaFisica', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nome: 'BBBBBB',
          cpf: 'BBBBBB',
          idade: 'BBBBBB',
          email: 'BBBBBB',
          telefone: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PessoaFisica', () => {
      const patchObject = Object.assign(
        {
          nome: 'BBBBBB',
          idade: 'BBBBBB',
          telefone: 'BBBBBB',
        },
        new PessoaFisica()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PessoaFisica', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          nome: 'BBBBBB',
          cpf: 'BBBBBB',
          idade: 'BBBBBB',
          email: 'BBBBBB',
          telefone: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a PessoaFisica', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPessoaFisicaToCollectionIfMissing', () => {
      it('should add a PessoaFisica to an empty array', () => {
        const pessoaFisica: IPessoaFisica = { id: 123 };
        expectedResult = service.addPessoaFisicaToCollectionIfMissing([], pessoaFisica);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pessoaFisica);
      });

      it('should not add a PessoaFisica to an array that contains it', () => {
        const pessoaFisica: IPessoaFisica = { id: 123 };
        const pessoaFisicaCollection: IPessoaFisica[] = [
          {
            ...pessoaFisica,
          },
          { id: 456 },
        ];
        expectedResult = service.addPessoaFisicaToCollectionIfMissing(pessoaFisicaCollection, pessoaFisica);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PessoaFisica to an array that doesn't contain it", () => {
        const pessoaFisica: IPessoaFisica = { id: 123 };
        const pessoaFisicaCollection: IPessoaFisica[] = [{ id: 456 }];
        expectedResult = service.addPessoaFisicaToCollectionIfMissing(pessoaFisicaCollection, pessoaFisica);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pessoaFisica);
      });

      it('should add only unique PessoaFisica to an array', () => {
        const pessoaFisicaArray: IPessoaFisica[] = [{ id: 123 }, { id: 456 }, { id: 61825 }];
        const pessoaFisicaCollection: IPessoaFisica[] = [{ id: 123 }];
        expectedResult = service.addPessoaFisicaToCollectionIfMissing(pessoaFisicaCollection, ...pessoaFisicaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const pessoaFisica: IPessoaFisica = { id: 123 };
        const pessoaFisica2: IPessoaFisica = { id: 456 };
        expectedResult = service.addPessoaFisicaToCollectionIfMissing([], pessoaFisica, pessoaFisica2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pessoaFisica);
        expect(expectedResult).toContain(pessoaFisica2);
      });

      it('should accept null and undefined values', () => {
        const pessoaFisica: IPessoaFisica = { id: 123 };
        expectedResult = service.addPessoaFisicaToCollectionIfMissing([], null, pessoaFisica, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pessoaFisica);
      });

      it('should return initial array if no PessoaFisica is added', () => {
        const pessoaFisicaCollection: IPessoaFisica[] = [{ id: 123 }];
        expectedResult = service.addPessoaFisicaToCollectionIfMissing(pessoaFisicaCollection, undefined, null);
        expect(expectedResult).toEqual(pessoaFisicaCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
