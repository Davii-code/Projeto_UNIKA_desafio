import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeletarEnderecoComponent } from './deletar-endereco.component';

describe('DeletarEnderecoComponent', () => {
  let component: DeletarEnderecoComponent;
  let fixture: ComponentFixture<DeletarEnderecoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DeletarEnderecoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DeletarEnderecoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
