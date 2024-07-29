import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BonEntreeDetailComponent } from './bon-entree-detail.component';

describe('BonEntreeDetailComponent', () => {
  let component: BonEntreeDetailComponent;
  let fixture: ComponentFixture<BonEntreeDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BonEntreeDetailComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BonEntreeDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
