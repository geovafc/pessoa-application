import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PessoaFisicaDetailComponent } from './pessoa-fisica-detail.component';

describe('PessoaFisica Management Detail Component', () => {
  let comp: PessoaFisicaDetailComponent;
  let fixture: ComponentFixture<PessoaFisicaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PessoaFisicaDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ pessoaFisica: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PessoaFisicaDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PessoaFisicaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load pessoaFisica on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.pessoaFisica).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
