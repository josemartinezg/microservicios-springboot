import { ComunesModule } from './comunes.module';

describe('ComunesModule', () => {
  let comunesModule: ComunesModule;

  beforeEach(() => {
    comunesModule = new ComunesModule();
  });

  it('should create an instance', () => {
    expect(comunesModule).toBeTruthy();
  });
});
