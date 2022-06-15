import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PessoaFisicaService } from '../service/pessoa-fisica.service';
import { IPessoaFisica, PessoaFisica } from '../pessoa-fisica.model';

import { PessoaFisicaUpdateComponent } from './pessoa-fisica-update.component';

describe('PessoaFisica Management Update Component', () => {
  let comp: PessoaFisicaUpdateComponent;
  let fixture: ComponentFixture<PessoaFisicaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let pessoaFisicaService: PessoaFisicaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PessoaFisicaUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(PessoaFisicaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PessoaFisicaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    pessoaFisicaService = TestBed.inject(PessoaFisicaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const pessoaFisica: IPessoaFisica = { id: 456 };

      activatedRoute.data = of({ pessoaFisica });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(pessoaFisica));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PessoaFisica>>();
      const pessoaFisica = { id: 123 };
      jest.spyOn(pessoaFisicaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pessoaFisica });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pessoaFisica }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(pessoaFisicaService.update).toHaveBeenCalledWith(pessoaFisica);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PessoaFisica>>();
      const pessoaFisica = new PessoaFisica();
      jest.spyOn(pessoaFisicaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pessoaFisica });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pessoaFisica }));
      saveSubject.complete();

      // THEN
      expect(pessoaFisicaService.create).toHaveBeenCalledWith(pessoaFisica);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PessoaFisica>>();
      const pessoaFisica = { id: 123 };
      jest.spyOn(pessoaFisicaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pessoaFisica });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(pessoaFisicaService.update).toHaveBeenCalledWith(pessoaFisica);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
