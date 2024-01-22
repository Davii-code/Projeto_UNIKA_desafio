import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeletarMonitoradorComponent } from './deletar-monitorador.component';

describe('DeletarMonitoradorComponent', () => {
  let component: DeletarMonitoradorComponent;
  let fixture: ComponentFixture<DeletarMonitoradorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DeletarMonitoradorComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DeletarMonitoradorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
