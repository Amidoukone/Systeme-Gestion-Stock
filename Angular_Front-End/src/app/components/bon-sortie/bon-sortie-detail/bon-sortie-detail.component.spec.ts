import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BonSortieDetailComponent } from './bon-sortie-detail.component';

describe('BonSortieDetailComponent', () => {
  let component: BonSortieDetailComponent;
  let fixture: ComponentFixture<BonSortieDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BonSortieDetailComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BonSortieDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
