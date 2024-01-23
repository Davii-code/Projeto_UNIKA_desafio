import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ImportaExcelComponent } from './importa-excel.component';

describe('ImportaExcelComponent', () => {
  let component: ImportaExcelComponent;
  let fixture: ComponentFixture<ImportaExcelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ImportaExcelComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ImportaExcelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
