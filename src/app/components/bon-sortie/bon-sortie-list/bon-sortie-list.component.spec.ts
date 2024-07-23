import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BonSortieListComponent } from './bon-sortie-list.component';

describe('BonSortieListComponent', () => {
  let component: BonSortieListComponent;
  let fixture: ComponentFixture<BonSortieListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BonSortieListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BonSortieListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
