import fs from 'fs';
import mv from 'mv';

const srcDirPath = 'dist';
const destDirPath = '../src/main/resources/static';

fs.rmSync(destDirPath, { recursive: true, force: true });

mv(srcDirPath, destDirPath, { mkdirp: true }, (err) => {
  if (err) {
    console.error('Error moving directory:', err);
  } else {
    console.log(`Moved ${srcDirPath} to ${destDirPath}`);
  }
});