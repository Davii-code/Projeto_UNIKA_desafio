import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MonitoradorComponent } from './monitorador.component';

describe('MonitoradorComponent', () => {
  let component: MonitoradorComponent;
  let fixture: ComponentFixture<MonitoradorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MonitoradorComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MonitoradorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
