import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditarMonitoradorComponent } from './editar-monitorador.component';

describe('EditarMonitoradorComponent', () => {
  let component: EditarMonitoradorComponent;
  let fixture: ComponentFixture<EditarMonitoradorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditarMonitoradorComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EditarMonitoradorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
