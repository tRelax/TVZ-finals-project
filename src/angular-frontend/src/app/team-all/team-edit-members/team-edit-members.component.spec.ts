import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeamEditMembersComponent } from './team-edit-members.component';

describe('TeamEditMembersComponent', () => {
  let component: TeamEditMembersComponent;
  let fixture: ComponentFixture<TeamEditMembersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TeamEditMembersComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TeamEditMembersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
