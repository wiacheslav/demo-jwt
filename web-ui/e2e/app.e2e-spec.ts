import { DemoJwtPage } from './app.po';

describe('demo-jwt App', () => {
  let page: DemoJwtPage;

  beforeEach(() => {
    page = new DemoJwtPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!!');
  });
});
