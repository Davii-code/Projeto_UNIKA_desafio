import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CadastroMonitoradorComponent } from './cadastro-monitorador.component';

describe('CadastroMonitoradorComponent', () => {
  let component: CadastroMonitoradorComponent;
  let fixture: ComponentFixture<CadastroMonitoradorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CadastroMonitoradorComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CadastroMonitoradorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
