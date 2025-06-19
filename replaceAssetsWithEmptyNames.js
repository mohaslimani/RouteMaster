const fs = require('fs');
const path = require('path');

function replaceImagesWithPlaceholders(dir) {
  const entries = fs.readdirSync(dir, { withFileTypes: true });

  for (const entry of entries) {
    const fullPath = path.join(dir, entry.name);

    if (entry.isDirectory()) {
      replaceImagesWithPlaceholders(fullPath);
    } else if (entry.isFile()) {
      const ext = path.extname(entry.name).toLowerCase();
      if (['.jpg', '.jpeg', '.png'].includes(ext)) {
        // Delete the image content and leave a placeholder
        fs.writeFileSync(fullPath, ''); // Create empty file with same name
      }
    }
  }
}

replaceImagesWithPlaceholders('./assets'); // Replace with your folder
