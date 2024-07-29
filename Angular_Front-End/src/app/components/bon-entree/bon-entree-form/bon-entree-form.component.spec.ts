import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BonEntreeFormComponent } from './bon-entree-form.component';

describe('BonEntreeFormComponent', () => {
  let component: BonEntreeFormComponent;
  let fixture: ComponentFixture<BonEntreeFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BonEntreeFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BonEntreeFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
