import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BonEntreeListComponent } from './bon-entree-list.component';

describe('BonEntreeListComponent', () => {
  let component: BonEntreeListComponent;
  let fixture: ComponentFixture<BonEntreeListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BonEntreeListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BonEntreeListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
